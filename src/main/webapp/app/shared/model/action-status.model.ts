export interface IActionStatus {
    id?: number;
    name?: string;
}

export class ActionStatus implements IActionStatus {
    constructor(public id?: number, public name?: string) {}
}
