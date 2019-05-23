export interface IReferralSource {
    id?: number;
    name?: string;
}

export class ReferralSource implements IReferralSource {
    constructor(public id?: number, public name?: string) {}
}
