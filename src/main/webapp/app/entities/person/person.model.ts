import { BaseEntity } from './../../shared';

export class Person implements BaseEntity {
    constructor(
        public id?: number,
        public email?: string,
        public firstname?: string,
        public lastname?: string,
        public birthday?: any,
        public stock?: BaseEntity,
        public grocerie?: BaseEntity,
        public blackList?: BaseEntity,
        public meals?: BaseEntity[],
        public goals?: BaseEntity[],
    ) {
    }
}
