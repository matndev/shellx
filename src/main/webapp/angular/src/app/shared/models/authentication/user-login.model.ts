export class UserLogin {

    private email: string;
    private password: string;
    private matchingPassword: string;

    /*public static fromJson(json: Object): User {
        return new User(
            json['id'],
            json['username']
            //new Date(json['published'])
        );
    }*/

    constructor(public p_email: string,
                public p_password: string,
                public p_matchingPassword: string
                ) {
        this.email = p_email;
        this.password = p_password;    
        this.matchingPassword = p_matchingPassword;        
    } 

    public getEmail() : string {
        return this.email;
    }

    public getPassword() : string {
        return this.password;
    }

    public getMatchingPassword() : string {
        return this.matchingPassword;
    }
}
