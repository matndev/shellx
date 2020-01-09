export class Notification {

    private id: number;
    private title: string;
    private description: string;
    private type: string;
    private idUser: number;
    private idRoom: number;
    private dateCreation: string;
    private read: boolean;

    constructor(    id?: number,
                    title?: string,
                    description?: string,
                    type?: string,
                    idUser?: number,
                    idRoom?: number,
                    dateCreation?: string,
                    read?: boolean ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.idUser = idUser;
        this.idRoom = idRoom;
        this.dateCreation = dateCreation;
        this.read = read;
    }

    public getId() : number {
        return this.id;
    }

    public setId(id: number) {
        this.id = id;
    }

    public getTitle() : string {
        return this.title;
    }

    public setTitle(title: string) {
        this.title = title;
    }
    
    public getDescription() : string {
        return this.description;
    }

    public setDescription(description: string) {
        this.description = description;
    }

    public getType() : string {
        return this.type;
    }

    public setType(type: string) {
        this.type = type;
    }

    public getIdUser() : number {
        return this.idUser;
    }

    public setIdUser(idUser: number) {
        this.idUser = idUser;
    }

    public getIdRoom() : number {
        return this.idRoom;
    }

    public setIdRoom(idRoom: number) {
        this.idRoom = idRoom;
    }

    public getDateCreation() : string {
        return this.dateCreation;
    }

    public setDateCreation(dateCreation: string) {
        this.dateCreation = dateCreation;
    }

    public getRead() : boolean {
        return this.read;
    }

    public setRead(read: boolean) {
        this.read = read;
    }
}
