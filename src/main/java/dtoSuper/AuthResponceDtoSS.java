package dtoSuper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class AuthResponceDtoSS {

    private boolean registration;
    private String status;
    private String token;
}
