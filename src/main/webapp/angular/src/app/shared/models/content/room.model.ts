import { Message } from './message.model';
import { User } from '../user.model';

export class Room {

    private id: number;
	private name: string;
	private roomAdmin: number;
	//private dateCreation: date;
	private enabled: boolean;
	private modePrivate: boolean;
	private messages: Message[];
	private users: User[];
	
    constructor(name?: string, 
                roomAdmin?: number, 
                enabled?: boolean, 
                modePrivate?: boolean, 
                messages?: Message[], 
                users?: User[]) {
        this.name = name;
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
