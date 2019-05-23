export interface IAltContact {
    id?: number;
    name?: string;
    relationship?: string;
    phone?: string;
    notes?: any;
    participantFirstName?: string;
    participantId?: number;
    createdByLogin?: string;
    createdById?: number;
}

export class AltContact implements IAltContact {
    constructor(
        public id?: number,
        public name?: string,
        public relationship?: string,
        public phone?: string,
        public notes?: any,
        public participantFirstName?: string,
        public participantId?: number,
        public createdByLogin?: string,
        public createdById?: number
    ) {}
}
