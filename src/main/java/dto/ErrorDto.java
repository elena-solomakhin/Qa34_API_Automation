package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class ErrorDto
{
    private int code;//409
    private String details;//api/login
    private String message;//"Wrong.."
    private String timetamp;// time
}
