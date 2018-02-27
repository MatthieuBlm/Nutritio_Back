/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { NutritioBackTestModule } from '../../../test.module';
import { IngredientEntryComponent } from '../../../../../../main/webapp/app/entities/ingredient-entry/ingredient-entry.component';
import { IngredientEntryService } from '../../../../../../main/webapp/app/entities/ingredient-entry/ingredient-entry.service';
import { IngredientEntry } from '../../../../../../main/webapp/app/entities/ingredient-entry/ingredient-entry.model';

describe('Component Tests', () => {

    describe('IngredientEntry Management Component', () => {
        let comp: IngredientEntryComponent;
        let fixture: ComponentFixture<IngredientEntryComponent>;
        let service: IngredientEntryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [IngredientEntryComponent],
                providers: [
                    IngredientEntryService
                ]
            })
            .overrideTemplate(IngredientEntryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IngredientEntryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IngredientEntryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new IngredientEntry(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ingredientEntries[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
