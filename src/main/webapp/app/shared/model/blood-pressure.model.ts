import { Moment } from 'moment';

export interface IBloodPressure {
    id?: number;
    timestamp?: Moment;
    systolic?: number;
    diastolic?: number;
    userLogin?: string;
    userId?: number;
}

export class BloodPressure implements IBloodPressure {
    constructor(
        public id?: number,
        public timestamp?: Moment,
        public systolic?: number,
        public diastolic?: number,
        public userLogin?: string,
        public userId?: number
    ) {}
}
