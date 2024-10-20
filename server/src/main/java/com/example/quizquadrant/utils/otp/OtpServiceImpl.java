package com.example.quizquadrant.utils.otp;

import com.example.quizquadrant.model.Otp;
import com.example.quizquadrant.repository.OtpRepository;
import com.example.quizquadrant.utils.email.EmailDetails;
import com.example.quizquadrant.utils.email.EmailService;
import com.example.quizquadrant.utils.error.BadRequestError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final EmailService emailService;
    private final OtpRepository otpRepository;

    private final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#^$%&*";
    private final int OTP_LENGTH = 8;
    private final int OTP_LIFE = 10 * 60 * 1000;  // 10 minutes


    @Override
    public void validateOtp(String email, String otp) throws Exception {
        boolean isOtpValid = isOtpValid(email, otp);
        if (!isOtpValid) {
            throw new BadRequestError("Invalid OTP");
        }
    }

    //    repository access methods
    @Override
    public Otp createOrUpdateOtp(String email) {
//        generate otp
        String otpString = generateOtp();
//        generate otp object
        Otp otp = Otp
                .builder()
                .email(email)
                .otp(otpString)
                .expiresOn(
                        new Timestamp(System.currentTimeMillis() + OTP_LIFE)
                                .toLocalDateTime()
                )
                .build();

//        check if otp is already present and act accordingly
        Optional<Otp> otpOptional = otpRepository.findById(email);
        if (otpOptional.isPresent()) {
            otp = otpOptional.get();
            otp.setOtp(otp.getOtp());
            otp.setExpiresOn(otp.getExpiresOn());
        }
        otp = otpRepository.save(otp);

        return otp;
    }


    //    helper methods for internal call
    private String generateOtp() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < OTP_LENGTH; i++) {
            sb.append(
                    LETTERS.charAt(random.nextInt(LETTERS.length()))
            );
        }
        return sb.toString();
    }

    private boolean isOtpValid(String email, String otp) {
//        check if otp exists in database
        Optional<Otp> otpOptional = otpRepository.findByEmailAndOtp(email, otp);
        if (otpOptional.isEmpty()) {
            return false;
        }

//        check if otp is expired
        Otp otpData = otpOptional.get();
        otpRepository.delete(otpData);
        return LocalDateTime.now().isBefore(otpData.getExpiresOn());
    }
}
