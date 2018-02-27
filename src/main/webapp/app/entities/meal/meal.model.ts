import { BaseEntity } from './../../shared';

export class Meal implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public date?: any,
        public recipes?: BaseEntity[],
        public person?: BaseEntity,
    ) {
    }
}
