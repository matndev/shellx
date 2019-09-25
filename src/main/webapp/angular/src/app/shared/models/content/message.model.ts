import { User } from '../authentication/user.model';

export class Message {

	private messageId: number;
	private messageAuthor: User;
	private messageReceiver: User;
    private messageContent: string;
    private messageDate: Date;
	private messageVisible: boolean;
    private messageEnabled: boolean;
    private messageRoomId: number;
    //private messageDate: date;
    
    constructor(messageAuthor?: User, 
                messageContent?: string,
                messageDate?: Date,
                messageEnabled?: boolean,
                messageReceiver?: User,
                messageVisible?: boolean,
                messageRoomId?: number) {
        this.messageAuthor = messageAuthor;
        this.messageContent = messageContent;
        this.messageDate = messageDate;
        this.messageEnabled = messageEnabled;
        this.messageReceiver = messageReceiver;
        this.messageVisible = messageVisible;
        this.messageRoomId = messageRoomId;
    }

    getMessageId(): number {
        return this.messageId;
    }

    getMessageAuthor(): User {
        return this.messageAuthor;
    }
    setMessageAuthor(author: User) {
        this.messageAuthor = author;
    }

    getMessageReceiver(): User {
        return this.messageReceiver;
    }
    setMessageReceiver(receiver: User) {
        this.messageReceiver = receiver;
    }

    getMessageContent(): string {
        return this.messageContent;
    }
    setMessageContent(content: string) {
        this.messageContent = content;
    }

    getMessageVisible(): boolean {
        return this.messageVisible;
    }
    setMessageVisible(visible: boolean) {
        this.messageVisible = visible;
    }

    getMessageEnabled(): boolean {
        return this.messageEnabled;
    }
    setMessageEnabled(enabled: boolean) {
        this.messageEnabled = enabled;
    }

    getMessageRoomId(): number {
        return this.messageRoomId;
    }
    setMessageRoomId(roomId: number) {
        this.messageRoomId = roomId;
    }    
}
