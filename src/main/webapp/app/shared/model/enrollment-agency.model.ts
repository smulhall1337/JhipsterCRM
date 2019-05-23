export interface IEnrollmentAgency {
    id?: number;
    name?: string;
    office?: string;
    recordNumber?: string;
    phone?: string;
}

export class EnrollmentAgency implements IEnrollmentAgency {
    constructor(public id?: number, public name?: string, public office?: string, public recordNumber?: string, public phone?: string) {}
}
