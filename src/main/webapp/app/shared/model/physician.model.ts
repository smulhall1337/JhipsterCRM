export interface IPhysician {
    id?: number;
    firstName?: string;
    lastName?: string;
    phone?: string;
    fax?: string;
    address?: string;
    notes?: any;
    status?: number;
    participantFirstName?: string;
    participantId?: number;
}

export class Physician implements IPhysician {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public phone?: string,
        public fax?: string,
        public address?: string,
        public notes?: any,
        public status?: number,
        public participantFirstName?: string,
        public participantId?: number
    ) {}
}
