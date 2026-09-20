package GroceryProject.OrderingProject.Service;

import GroceryProject.OrderingProject.Entity.Order;
import GroceryProject.OrderingProject.Entity.Payment;
import GroceryProject.OrderingProject.Enum.PaymentStatus;
import GroceryProject.OrderingProject.Repository.PaymentRepository;
import GroceryProject.OrderingProject.dto.RazorpayCheckoutResponse;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;

@Service
public class PaymentService {

   private final RazorpayClient razorpayClient;
   private final PaymentRepository paymentRepository;
    @Value("${razorpay.api.key}")
    String apiKey;
    @Value("${razorpay.api.secret}")
    String apiSecret;

    public PaymentService(@Value("${razorpay.api.key}")String apiKey, @Value("${razorpay.api.secret}")String apiSecret, PaymentRepository paymentRepository) throws RazorpayException {
        this.razorpayClient = new RazorpayClient(apiKey,apiSecret);
        this.paymentRepository = paymentRepository;
    }

    //step1:Create razorpay order+createPayment
    @Transactional
    public Payment createPayment(Order order) throws RazorpayException {
        JSONObject request=new JSONObject();

        request.put("amount",order.getTotalAmount()*100);
        request.put("currency","INR");
        request.put("receipt","order_" + order.getId());

        com.razorpay.Order razorpayOrder=razorpayClient.orders.create(request);

        // save in payment entity
        Payment payment=Payment.builder()
                .order(order)
                .amount(order.getTotalAmount())
                .currency("INR")
                .status(PaymentStatus.PENDING)
                .razorpayOrderId(razorpayOrder.get("id")) //razorpay order
                .createdAt(LocalDateTime.now())
                .build();

        return paymentRepository.save(payment);


    }

   //step2 verify payment after checkout
    @Transactional
   public Payment verifyAndSavePayment(String razorpayOrderId,String razorpayPaymentId,String razorpaySignature,Order order){
       //step1 verify signature
        String payload=razorpayOrderId + "|" +razorpayPaymentId;
        String expectedSignature=hmacSha256(payload,apiSecret);

        boolean isValid=expectedSignature.equals(razorpaySignature);

        //step2 fetch existing payment
       Payment payment=paymentRepository.findByOrder(order)
               .orElseThrow(()->new RuntimeException("Payment not found"));

       //step 3 update payment entity
       payment.setRazorpayPaymentId(razorpayPaymentId);
       payment.setTransactionId(razorpayPaymentId);//optional for backward compatibility

       payment.setStatus(isValid ? PaymentStatus.SUCCESS: PaymentStatus.FAILED);
       payment.setPaymentMethod("razorpay");
       payment.setUpdatedAt(LocalDateTime.now());

       return paymentRepository.save(payment);



   }

   public String hmacSha256(String payload,String secret){
        try{
            Mac mac=Mac.getInstance("HmacSHA256");
            SecretKeySpec key=new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),"HmacSHA256");
            mac.init(key);
            //byte[] hashBytes=mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(mac.doFinal(payload.getBytes(StandardCharsets.UTF_8)));


        } catch (NoSuchAlgorithmException  | InvalidKeyException ex) {
            throw new IllegalStateException("Unable to verify Razorpay signature",ex);
        }


   }

   @Transactional
   public Payment refundPayment(Order order){

        Payment payment=paymentRepository.findByOrder(order)
                .orElseThrow(()->new RuntimeException("payment not found"));

        if(payment.getStatus()!=PaymentStatus.SUCCESS){
            throw new RuntimeException("Only Successful payments can be refunded");
        }

        try{
            JSONObject refund=new JSONObject();
            refund.put("payment_id",payment.getRazorpayPaymentId());
            refund.put("amount",payment.getAmount()*100);

           com.razorpay.Refund razorpayRefund =razorpayClient.payments.refund(refund);

            payment.setStatus(PaymentStatus.REFUNDED);
            payment.setUpdatedAt(LocalDateTime.now());
            payment.setTransactionId(razorpayRefund.get("id")); // store refund ID
            return paymentRepository.save(payment);


        }catch (RazorpayException e){
            throw new RuntimeException("Refund failed: "+e.getMessage(),e);
        }

   }

    public RazorpayCheckoutResponse getCheckoutDetails(Order order) {
        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return new RazorpayCheckoutResponse(
                order.getId(),
                apiKey, // Razorpay public Key ID; never expose apiSecret
                payment.getRazorpayOrderId(),
                Math.round(payment.getAmount() * 100),
                payment.getCurrency()
        );
    }
}
