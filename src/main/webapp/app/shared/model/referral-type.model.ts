export interface IReferralType {
    id?: number;
    name?: string;
}

export class ReferralType implements IReferralType {
    constructor(public id?: number, public name?: string) {}
}
