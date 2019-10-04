import { User } from '../authentication/user.model';

export class Message {

	private messageId: number;
	private messageAuthor: number;
	private messageReceiver: number;
    private messageContent: string;
    private messageDate: Date;
	private messageVisible: boolean;
    private messageEnabled: boolean;
    private messageRoomId: number;
    
    constructor(messageAuthor?: number, 
                messageContent?: string,
                messageDate?: Date,
                messageEnabled?: boolean,
                messageReceiver?: number,
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

    getMessageAuthor(): number {
        return this.messageAuthor;
    }
    setMessageAuthor(author: number) {
        this.messageAuthor = author;
    }

    getMessageReceiver(): number {
        return this.messageReceiver;
    }
    setMessageReceiver(receiver: number) {
        this.messageReceiver = receiver;
    }

    getMessageContent(): string {
        return this.messageContent;
    }
    setMessageContent(content: string) {
        this.messageContent = content;
    }

    getMessageDate(): Date {
        return this.messageDate;
    }
    setMessageDate(date: Date) {
        this.messageDate = date;
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
