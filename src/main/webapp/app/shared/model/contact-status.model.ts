export interface IContactStatus {
    id?: number;
    name?: string;
}

export class ContactStatus implements IContactStatus {
    constructor(public id?: number, public name?: string) {}
}
