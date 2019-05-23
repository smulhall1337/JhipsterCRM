import { Moment } from 'moment';

export interface IAction {
    id?: number;
    dueDate?: Moment;
    startDateTime?: Moment;
    endDateTime?: Moment;
    notes?: any;
    participantFirstName?: string;
    participantLastName?: string;
    participantId?: number;
    userLogin?: string;
    userFistName?: string;
    userLastName?: string;
    userId?: number;
    actionSubTypeName?: string;
    actionSubTypeId?: number;
    actionTypeName?: string;
    actionTypeId?: number;
    actionStatusName?: string;
    actionStatusId?: number;
}

export class Action implements IAction {
    constructor(
        public id?: number,
        public dueDate?: Moment,
        public startDateTime?: Moment,
        public endDateTime?: Moment,
        public notes?: any,
        public participantFirstName?: string,
        public participantLastName?: string,
        public participantId?: number,
        public userLogin?: string,
        public userFirstName?: string,
        public userLastName?: string,
        public userId?: number,
        public actionSubTypeName?: string,
        public actionSubTypeId?: number,
        public actionTypeName?: string,
        public actionTypeId?: number,
        public actionStatusName?: string,
        public actionStatusId?: number
    ) {}
}
