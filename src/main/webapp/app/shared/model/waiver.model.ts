export interface IWaiver {
    id?: number;
    name?: string;
}

export class Waiver implements IWaiver {
    constructor(public id?: number, public name?: string) {}
}
