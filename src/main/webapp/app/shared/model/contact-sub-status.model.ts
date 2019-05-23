export interface IContactSubStatus {
    id?: number;
    name?: string;
    subTypeOfName?: string;
    subTypeOfId?: number;
}

export class ContactSubStatus implements IContactSubStatus {
    constructor(public id?: number, public name?: string, public subTypeOfName?: string, public subTypeOfId?: number) {}
}
