export class Message {

	private messageId: number;
	private messageAuthor: string;
	private messageReceiver: string;
    private messageContent: string;
    private messageDate: Date;
	private messageVisible: boolean;
	private messageEnabled: boolean;
    //private messageDate: date;
    
    constructor(messageAuthor?: string, 
                messageContent?: string,
                messageDate?: Date,
                messageEnabled?: boolean,
                messageReceiver?: string,
                messageVisible?: boolean) {
        this.messageAuthor = messageAuthor;
        this.messageContent = messageContent;
        this.messageDate = messageDate;
        this.messageEnabled = messageEnabled;
        this.messageReceiver = messageReceiver;
        this.messageVisible = messageVisible;
    }

    getMessageId(): number {
        return this.messageId;
    }

    getMessageAuthor(): string {
        return this.messageAuthor;
    }
    setMessageAuthor(author: string) {
        this.messageAuthor = author;
    }

    getMessageReceiver(): string {
        return this.messageReceiver;
    }
    setMessageReceiver(receiver: string) {
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
}
