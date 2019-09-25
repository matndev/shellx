export class User {

    private id: number;
    private username: string;
    private email: string;

    /*public static fromJson(json: Object): User {
        return new User(
            json['id'],
            json['username']
            //new Date(json['published'])
        );
    }*/

    constructor(public p_username: string,
                public p_email?: string,
                public p_id?: number
                ) {
        this.username = p_username;
        this.email = p_email; 
        this.id = p_id;       
    } 

    public getUsername() : string {
        return this.username;
    }

    public getEmail() : string {
        return this.email;
    }

    public getId() : number {
        return this.id;
    }
}
