import { Moment } from 'moment';

export interface IWeight {
    id?: number;
    timestamp?: Moment;
    weight?: number;
    userLogin?: string;
    userId?: number;
}

export class Weight implements IWeight {
    constructor(public id?: number, public timestamp?: Moment, public weight?: number, public userLogin?: string, public userId?: number) {}
}
