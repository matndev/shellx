export class User {

    id: number;
    username: string;
    email: string;
    role: number;
    avatar: string;

    /*public static fromJson(json: Object): User {
        return new User(
            json['id'],
            json['username']
            //new Date(json['published'])
        );
    }*/

    constructor(username: string,
                email?: string,
                id?: number,
                role?: number,
                avatar?: string
                ) {
        this.username = username;
        this.email = email; 
        this.id = id; 
        this.role = role;
        this.avatar = avatar;   
    } 

    getUsername() : string {
        return this.username;
    }
    setUsername(username: string) {
        this.username = username;
    }    

    getEmail() : string {
        return this.email;
    }
    setEmail(email: string) {
        this.email = email;
    }     

    getId() : number {
        return this.id;
    }
    setId(id: number) {
        this.id = id;
    }     

    getRole() : number {
        return this.role;
    }
    setRole(role: number) {
        this.role = role;
    }     
   
    getAvatar() : string {
        return this.avatar;
    }  
    setAvatar(avatar: string) {
        this.avatar = avatar;
    }
    
    toString() : string {
        return "USER object: id: "+this.id+", username: "+this.username+", email: "+this.email+", role: "+this.role+", avatar: "+this.avatar;
    }
}
