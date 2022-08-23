package dtoSuper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class ErrorDtoSS {


        private int code;
        private String details;//api/login
        private String message;//"Wrong.."
        private String timetamp;

}
