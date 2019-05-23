export interface IMCO {
    id?: number;
    name?: string;
}

export class MCO implements IMCO {
    constructor(public id?: number, public name?: string) {}
}
