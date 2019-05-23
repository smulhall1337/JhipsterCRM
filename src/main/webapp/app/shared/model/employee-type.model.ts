export interface IEmployeeType {
    id?: number;
    name?: string;
}

export class EmployeeType implements IEmployeeType {
    constructor(public id?: number, public name?: string) {}
}
