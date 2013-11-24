package models;

import javax.validation.*;

import play.data.validation.Constraints.*;

public class User {
	@Required
    @MinLength(value = 4)
	@MaxLength (value = 20)
    public String nickName;
    @Required
    @Email
    public String email;
    @Required
    @MinLength(value = 6)
    public String password;
    @MinLength(value = 3)
    @MaxLength (value = 20)
    public String firstName;
    
    @MinLength(value = 3)
    @MaxLength (value = 20)
    public String lastName;
    
    public String logoPath;
}
