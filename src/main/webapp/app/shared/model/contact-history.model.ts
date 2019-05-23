import { Moment } from 'moment';

export interface IContactHistory {
    id?: number;
    date?: Moment;
    notes?: any;
    status?: number;
    participantFirstName?: string;
    participantId?: number;
    userLogin?: string;
    userId?: number;
    contactTypeName?: string;
    contactTypeId?: number;
    contactSubTypeName?: string;
    contactSubTypeId?: number;
}

export class ContactHistory implements IContactHistory {
    constructor(
        public id?: number,
        public date?: Moment,
        public notes?: any,
        public status?: number,
        public participantFirstName?: string,
        public participantId?: number,
        public userLogin?: string,
        public userId?: number,
        public contactTypeName?: string,
        public contactTypeId?: number,
        public contactSubTypeName?: string,
        public contactSubTypeId?: number
    ) {}
}
