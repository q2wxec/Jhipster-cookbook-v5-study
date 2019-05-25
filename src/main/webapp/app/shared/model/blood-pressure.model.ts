import { Moment } from 'moment';

export interface IBloodPressure {
    id?: number;
    timestamp?: Moment;
    Systolic?: number;
    Diastolic?: number;
    userLogin?: string;
    userId?: number;
}

export class BloodPressure implements IBloodPressure {
    constructor(
        public id?: number,
        public timestamp?: Moment,
        public Systolic?: number,
        public Diastolic?: number,
        public userLogin?: string,
        public userId?: number
    ) {}
}
