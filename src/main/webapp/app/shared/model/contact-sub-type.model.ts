export interface IContactSubType {
    id?: number;
    name?: string;
}

export class ContactSubType implements IContactSubType {
    constructor(public id?: number, public name?: string) {}
}
