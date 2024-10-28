package User;

public class User {
    private int id;
    private String UserName;
    private String Password;
}
private User  User;

public User(int id, String UserName, String Password) {
    this.id = id;
    this.UserName = UserName;
    this.Password = Password;
}

/*id*/
public int getId()
{
    return id;
}
public void setId(int id)
{
    this.id = id;
}

/*UserName*/
public String getUserName()
{
    return UserName;
}
public void setUserName(String UserName)
{
    this.UserName = UserName;
}

/*Password*/
public String getPassword()
{
    return Password;
}
public void setPassword(String Password)
{
    this.Password = Password;
}
