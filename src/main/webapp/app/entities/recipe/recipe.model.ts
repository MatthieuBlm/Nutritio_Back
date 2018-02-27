import { BaseEntity } from './../../shared';

export class Recipe implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public image?: string,
        public ingredientEntries?: BaseEntity[],
        public meals?: BaseEntity[],
    ) {
    }
}
