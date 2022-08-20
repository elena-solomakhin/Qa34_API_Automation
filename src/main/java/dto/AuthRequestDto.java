package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//{
//        "email": "noa@gmail.com",
//        "password": "Nnoa12345$"
//        } json
@Setter
@Getter
@ToString
@Builder
public class AuthRequestDto {

  private   String email;
  private String password ;
}
