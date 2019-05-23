export interface IActionSubType {
    id?: number;
    name?: string;
    subTypeOfName?: string;
    subTypeOfId?: number;
}

export class ActionSubType implements IActionSubType {
    constructor(public id?: number, public name?: string, public subTypeOfName?: string, public subTypeOfId?: number) {}
}
