export interface IEmployeeSubType {
    id?: number;
    name?: string;
}

export class EmployeeSubType implements IEmployeeSubType {
    constructor(public id?: number, public name?: string) {}
}
