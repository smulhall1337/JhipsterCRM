export interface IActionType {
    id?: number;
    name?: string;
}

export class ActionType implements IActionType {
    constructor(public id?: number, public name?: string) {}
}
