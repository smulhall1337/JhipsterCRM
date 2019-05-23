export interface IParticipantNotes {
    id?: number;
    notes?: any;
    userLogin?: string;
    userId?: number;
    participantFirstName?: string;
    participantId?: number;
}

export class ParticipantNotes implements IParticipantNotes {
    constructor(
        public id?: number,
        public notes?: any,
        public userLogin?: string,
        public userId?: number,
        public participantFirstName?: string,
        public participantId?: number
    ) {}
}
