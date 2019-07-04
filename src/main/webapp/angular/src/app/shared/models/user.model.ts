export class User {

    private id: number;
    private username: string;
    private email: string;
    private password: string;

    /*public static fromJson(json: Object): User {
        return new User(
            json['id'],
            json['username']
            //new Date(json['published'])
        );
    }*/

    constructor(public p_email: string,
                public p_password: string,
                ) {
        this.email = p_email;
        this.password = p_password;            
    } 
}
