package com.example.vuebackboard.service;

import com.example.vuebackboard.dto.UserDTO;
import com.example.vuebackboard.entity.UserEntity;
import com.example.vuebackboard.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (userEntity.getUserId().equals(username)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new User(userEntity.getUserId(), userEntity.getUserPw(), authorities);
    }
    
    // 회원가입
    public UserEntity register(UserDTO userDTO) {
    	
    	UserEntity id = UserEntity.builder().userId(userDTO.getUserId()).build();
    	String userId = id.getUserId();
    	
		if(userRepository.existsByUserId(userId)) {
			log.warn("이미 존재하는 아이디 입니다.");
			throw new RuntimeException("이미 존재하는 아이디 입니다.");
		}
    	
    	String encPassword = passwordEncoder.encode(userDTO.getUserPw());
    	
    	UserEntity entity = UserEntity.builder()
                .userId(userDTO.getUserId())
                .userEmail(userDTO.getUserEmail())
                .userName(userDTO.getUserName())
                .userPw(encPassword)
                .build();
        return userRepository.save(entity);
    }
    
    // ID 찾기
    public String findId(UserDTO userDTO) {
    	String userId = userRepository.findIdByEmailAndName(userDTO.getUserEmail(), userDTO.getUserName());
    	
    	return userId;
    }
    
    // 임시 패스워드 생성
 	public String getTmpPassword() {
 		char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

         String pwd = "";

         // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합
         int idx = 0;
         for(int i = 0; i < 10; i++){
             idx = (int) (charSet.length * Math.random());
             pwd += charSet[idx];
         }

         log.info("임시 비밀번호 생성 : " + pwd);

         return pwd;
 	}
 	
 	// 임시 패스워드로 업데이트
 	public void updatePassword(String tmpPassword, String userEmail) {
 		String encryptPassword = passwordEncoder.encode(tmpPassword);
         if(userRepository.findByUserEmail(userEmail) != null) {
        	 
         	UserEntity user = userRepository.findByUserEmail(userEmail);
         	user.updatePassword(encryptPassword);
        	userRepository.save(user);
        	
         	log.info("임시 비밀번호 업데이트 : " + tmpPassword);
         } else {
         	throw new IllegalArgumentException("해당 사용자가 없습니다."); 
         }

 	}
    
    // 이메일 존재 검증
 	public boolean checkEmail(String memberEmail) {
 		return userRepository.existsByUserEmail(memberEmail);
 	}
    
    public MimeMessage findPw(String tmpPassword, String userEmail) throws Exception {
		MimeMessage message = javaMailSender.createMimeMessage();
		
		UserEntity user = userRepository.findByUserEmail(userEmail);
		
		String msg = "";
		message.addRecipients(RecipientType.TO, userEmail);
		message.setSubject("[Spring-facebook] 임시 비밀번호 발급 안내");
		
		msg+="<div>";
	    msg+="<div>";
	    msg+="<div><u></u>";
	    msg+="<div style='margin:0;padding:0' bgcolor='#ffffff'>";
	    msg+="<table align='center' border='0' cellspacing='0' cellpadding='0' style='border-collapse:collapse'>";
	    msg+="<tbody>";
	    msg+="<tr>";
	    msg+="<td style='font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;background:#ffffff'>";
	    msg+="<table border='0' width='100%' cellspacing='0' cellpadding='0' style='border-collapse:collapse'>";
	    msg+="<tbody>";
	    msg+="<tr>";
	    msg+="<td height='20' style='line-height:20px' colspan='3'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td height='1' colspan='3' style='line-height:1px'><span style='color:#ffffff;font-size:1px;opacity:0'>회원님의 Facebook 비밀번호 재설정 요청을 받았습니다.</span></td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td width='15' style='display:block;width:15px'>&nbsp;&nbsp;&nbsp;</td>";
	    msg+="<td>";
	    msg+="<table border='0' width='100%' cellspacing='0' cellpadding='0' style='border-collapse:collapse'>";
	    msg+="<tbody>";
	    msg+="<tr>";
	    msg+="<td height='15' style='line-height:15px' colspan='3'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td align='left' width='32' style='height:32;line-height:0px'><img width='32' src='https://static.xx.fbcdn.net/rsrc.php/v3/yc/r/I92GqZOkKcu.png' height='32' style='border:0'></td>";
	    msg+="<td width='15' style='display:block;width:15px'>&nbsp;&nbsp;&nbsp;</td>";
	    msg+="<td width='100%'><span style='font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:19px;line-height:32px;color:#1877f2'></span></td>";
	    msg+="</tr>";
	    msg+="<tr style='border-bottom:solid 1px #e5e5e5'>";
	    msg+="<td height='15' style='line-height:15px' colspan='3'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table>";
	    msg+="</td>";
	    msg+="<td width='15' style='display:block;width:15px'>&nbsp;&nbsp;&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td width='15' style='display:block;width:15px'>&nbsp;&nbsp;&nbsp;</td>";
	    msg+="<td>";
	    msg+="<table border='0' width='100%' cellspacing='0' cellpadding='0' style='border-collapse:collapse'>";
	    msg+="<tbody>";
	    msg+="<tr>";
	    msg+="<td height='4' style='line-height:4px'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td><span style='font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:16px;line-height:21px;color:#141823'><span style='font-size:15px'>";
	    msg+="<p></p>";
	    msg+="<div style='margin-top:16px;margin-bottom:20px'>" + user.getUserName() + " 님, 안녕하세요!</div>";
	    msg+="<div>회원님의 Facebook 비밀번호 재설정 요청을 " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " 에 받았습니다.</div>발급된 임시 비밀번호를 통해 로그인 후 원하는 비밀번호로 변경하세요:<p></p>";
	    msg+="<table border='0' cellspacing='0' cellpadding='0' style='border-collapse:collapse;width:max-content;margin-top:20px;margin-bottom:20px'>";
	    msg+="<tbody>";
	    msg+="<tr>";
	    msg+="<td style='font-size:11px;font-family:LucidaGrande,tahoma,verdana,arial,sans-serif;padding:14px 32px 14px 32px;background-color:#f2f2f2;border-left:1px solid #ccc;border-right:1px solid #ccc;border-top:1px solid #ccc;border-bottom:1px solid #ccc;text-align:center;border-radius:7px;display:block;border:1px solid #1877f2;background:#e7f3ff'><span style='font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:16px;line-height:21px;color:#141823'><span style='font-size:17px;font-family:Roboto;font-weight:700;margin-left:0px;margin-right:0px'>" + tmpPassword + "</span></span></td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table>또는 비밀번호를 직접 변경할 수도 있습니다.<table border='0' width='100%' cellspacing='0' cellpadding='0' style='border-collapse:collapse'>";
	    msg+="<tbody>";
	    msg+="<tr>";
	    msg+="<td height='20' style='line-height:20px'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td align='middle'><a href='#' style='color:#1b74e4;text-decoration:none' target='_blank'>";
	    msg+="<table border='0' width='100%' cellspacing='0' cellpadding='0' style='border-collapse:collapse'>";
	    msg+="<tbody>";
	    msg+="<tr>";
	    msg+="<td style='border-collapse:collapse;border-radius:6px;text-align:center;display:block;background:#1877f2;padding:8px 20px 8px 20px'><a href='#' style='color:#1b74e4;text-decoration:none;display:block' target='_blank'>";
	    msg+="<font><span style='font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;white-space:nowrap;font-weight:bold;vertical-align:middle;color:#ffffff;font-weight:500;font-family:Roboto-Medium,Roboto,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:17px'>비밀번호&nbsp;변경</span></font>";
	    msg+="</a></td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table>";
	    msg+="</a></td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td height='8' style='line-height:8px'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td height='20' style='line-height:20px'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table><br>";
	    msg+="<div><span style='color:#333333;font-weight:bold'>이 변동 요청을 하지 않으셨나요?</span></div>새 비밀번호를 요청하지 않으셨다면 <a href='https://www.facebook.com/login/recover/cancel/?n=735812&amp;id=100092282715924&amp;i=www' style='color:#0a7cff;text-decoration:none' target='_blank'>저희에게 알려주세요</a>.";
	    msg+="</span></span></td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td height='50' style='line-height:50px'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table>";
	    msg+="</td>";
	    msg+="<td width='15' style='display:block;width:15px'>&nbsp;&nbsp;&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td width='15' style='display:block;width:15px'>&nbsp;&nbsp;&nbsp;</td>";
	    msg+="<td>";
	    msg+="<table align='left' border='0' width='100%' cellspacing='0' cellpadding='0' style='border-collapse:collapse'>";
	    msg+="<tbody>";
	    msg+="<tr style='border-top:solid 1px #e5e5e5'>";
	    msg+="<td height='19' style='line-height:19px'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td style='font-family:Roboto-Regular,Roboto,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:11px;color:#8a8d91;line-height:16px;font-weight:400'>";
	    msg+="<table border='0' cellspacing='0' cellpadding='0' style='border-collapse:collapse;color:#8a8d91;text-align:center;font-size:12px;font-weight:400;font-family:Roboto-Regular,Roboto,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif'>";
	    msg+="<tbody>";
	    msg+="<tr>";
	    msg+="<td align='center' style='font-size:12px;font-family:Roboto-Regular,Roboto,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;color:#8a8d91;text-align:center;font-weight:400;padding-bottom:6px'>from</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td align='center' style='font-size:12px;font-family:Roboto-Regular,Roboto,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;color:#8a8d91;text-align:center;font-weight:400;padding-top:6px;padding-bottom:6px'><img width='74' height='22' src='https://facebook.com/images/email/meta_logo.png' style='border:0'></td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td align='center' style='font-size:12px;font-family:Roboto-Regular,Roboto,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;color:#8a8d91;text-align:center;font-weight:400;padding-top:6px;padding-bottom:6px'>© Facebook. Meta Platforms, Inc., Attention: Community Support, 1 Facebook Way, Menlo Park, CA 94025</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td align='center' style='font-size:12px;font-family:Roboto-Regular,Roboto,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;color:#8a8d91;text-align:center;font-weight:400;padding-top:6px'>이 메시지는 <a style='color:#1b74e4;text-decoration:none' href='mailto:testsmtp1773@gmail.com' target='_blank'>testsmtp1773@gmail.com</a> 주소로 전송되었습니다. <br>계정 보안을 위해 이 이메일은 전달하지 마세요. <a style='color:#1b74e4;text-decoration:none' href='https://www.facebook.com/help/213481848684090/' target='_blank'>더 알아보기</a></td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table>";
	    msg+="</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td height='10' style='line-height:10px'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table>";
	    msg+="</td>";
	    msg+="<td width='15' style='display:block;width:15px'>&nbsp;&nbsp;&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td height='20' style='line-height:20px' colspan='3'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table><span><img src='https://www.facebook.com/email_open_log_pic.php?mid=5fabf81359386G5b088cf4bf14G5fabfcacb9658G178' style='border:0;width:1px;height:1px' te6d2yrib=''></span>";
	    msg+="</td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table>";
	    msg+="</div>";
	    msg+="</div>";
	    msg+="</div>";
	    msg+="</div>";
	    message.setText(msg, "UTF-8", "HTML");
	    message.setFrom(new InternetAddress("testsmtp1773@gmail.com", "Spring-bbs"));
		
		javaMailSender.send(message);
		return message;
		
	}
}