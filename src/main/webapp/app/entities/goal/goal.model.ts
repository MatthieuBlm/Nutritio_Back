import { BaseEntity } from './../../shared';

export class Goal implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public energy?: number,
        public protein?: number,
        public carbohydrate?: number,
        public fat?: number,
        public sugar?: number,
        public saturatedFat?: number,
        public fibre?: number,
        public person?: BaseEntity,
    ) {
    }
}
