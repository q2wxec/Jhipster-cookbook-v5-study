export const enum Units {
    KG = 'KG',
    LB = 'LB'
}

export interface IGoalSettings {
    id?: number;
    weeklyGoal?: number;
    weightUnits?: Units;
    userLogin?: string;
    userId?: number;
}

export class GoalSettings implements IGoalSettings {
    constructor(
        public id?: number,
        public weeklyGoal?: number,
        public weightUnits?: Units,
        public userLogin?: string,
        public userId?: number
    ) {}
}
