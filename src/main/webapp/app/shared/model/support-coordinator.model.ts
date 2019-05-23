import { Moment } from 'moment';

export interface ISupportCoordinator {
    id?: number;
    firstName?: string;
    lastName?: string;
    phone?: string;
    emailId?: string;
    dateHired?: Moment;
    userName?: string;
    departmentName?: string;
    departmentId?: number;
    employeeTypeName?: string;
    employeeTypeId?: number;
    employeeSubTypeName?: string;
    employeeSubTypeId?: number;
}

export class SupportCoordinator implements ISupportCoordinator {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public phone?: string,
        public emailId?: string,
        public dateHired?: Moment,
        public userName?: string,
        public departmentName?: string,
        public departmentId?: number,
        public employeeTypeName?: string,
        public employeeTypeId?: number,
        public employeeSubTypeName?: string,
        public employeeSubTypeId?: number
    ) {}
}
