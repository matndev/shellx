import { Message } from './message.model';
import { User } from '../authentication/user.model';

export class Room {

    private id: number;
	private name: string;
	private description: string;
	private roomAdmin: number;
	//private dateCreation: date;
	private enabled: boolean;
	private modePrivate: boolean;
	private messages: Message[];
	public users: User[];
	
    constructor(id?: number,
				name?: string,
				description?: string,
                roomAdmin?: number, 
                enabled?: boolean, 
                modePrivate?: boolean, 
                messages?: Message[], 
                users?: User[]) {
		this.id = id;
		this.name = name;
		this.description = description;
        this.roomAdmin = roomAdmin;
        this.enabled = enabled;
        this.modePrivate = modePrivate;
        this.messages = messages;
        this.users = users;
    }

	getId(): number {
		return this.id;
	}

	getName(): string {
		return this.name;
	}
	setName(name: string) {
		this.name = name;
	}

	getDescription(): string {
		return this.description;
	}
	setDescription(description: string) {
		this.description = description;
	}	

	getRoomAdmin(): number {
		return this.roomAdmin;
	}
	setRoomAdmin(roomAdmin: number) {
		this.roomAdmin = roomAdmin;
	}

	isEnabled(): boolean {
		return this.enabled;
	}
	setEnabled(enabled: boolean) {
		this.enabled = enabled;
	}

	isModePrivate(): boolean {
		return this.modePrivate;
	}
	setModePrivate(modePrivate: boolean) {
		this.modePrivate = modePrivate;
	}

	getMessages(): Message[] {
		return this.messages;
	}
	setMessages(messages: Message[]) {
		this.messages = messages;
	}

	getUsers(): User[] {
		return this.users;
	}
	setUsers(users: User[]) {
		this.users = users;
	}
}
