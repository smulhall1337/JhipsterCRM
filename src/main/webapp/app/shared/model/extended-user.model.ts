export interface IExtendedUser {
    id?: number;
    departmentName?: string;
    departmentId?: number;
    employeeTypeName?: string;
    employeeTypeId?: number;
    employeeSubTypeName?: string;
    employeeSubTypeId?: number;
    userId?: number;
}

export class ExtendedUser implements IExtendedUser {
    constructor(
        public id?: number,
        public departmentName?: string,
        public departmentId?: number,
        public employeeTypeName?: string,
        public employeeTypeId?: number,
        public employeeSubTypeName?: string,
        public employeeSubTypeId?: number,
        public userId?: number
    ) {}
}
