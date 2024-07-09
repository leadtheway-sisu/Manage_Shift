package eight.pj.trial.entity;

public class LoginUserDto { 
      
    private String name;  
    
    private String password;  
   
    private String employeeid;  
    
    private String role;

    
	public String getName() {
        return name;  
    }

    public void setName(String name) {
        this.name = name; 
    }

    public String getPassword() {
        return password;  
    }

    public void setPassword(String password) {
        this.password = password;  
    }

    public String getEmployeeId() {
        return employeeid; 
    }

    public void setEmployeeId(String employeeid) {
        this.employeeid = employeeid;  
    }
    
    public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "LoginUserDto [name=" + name + ", password=" + password + ", employeeid=" + employeeid + ", role=" + role
				+ "]";
	}
	
}
