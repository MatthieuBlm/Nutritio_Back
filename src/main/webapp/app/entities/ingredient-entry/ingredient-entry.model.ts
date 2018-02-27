import { BaseEntity } from './../../shared';

export const enum Unit {
    'GRAMM',
    'MILLILITRE',
    'UNIT',
    'TEASPOON',
    'TABLESPOON',
    'CUP'
}

export class IngredientEntry implements BaseEntity {
    constructor(
        public id?: number,
        public amount?: number,
        public unit?: Unit,
        public ingredient?: BaseEntity,
        public stocks?: BaseEntity[],
        public recipes?: BaseEntity[],
        public groceries?: BaseEntity[],
        public blackLists?: BaseEntity[],
    ) {
    }
}
