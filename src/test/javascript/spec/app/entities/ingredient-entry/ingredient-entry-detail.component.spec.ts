/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { NutritioBackTestModule } from '../../../test.module';
import { IngredientEntryDetailComponent } from '../../../../../../main/webapp/app/entities/ingredient-entry/ingredient-entry-detail.component';
import { IngredientEntryService } from '../../../../../../main/webapp/app/entities/ingredient-entry/ingredient-entry.service';
import { IngredientEntry } from '../../../../../../main/webapp/app/entities/ingredient-entry/ingredient-entry.model';

describe('Component Tests', () => {

    describe('IngredientEntry Management Detail Component', () => {
        let comp: IngredientEntryDetailComponent;
        let fixture: ComponentFixture<IngredientEntryDetailComponent>;
        let service: IngredientEntryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [IngredientEntryDetailComponent],
                providers: [
                    IngredientEntryService
                ]
            })
            .overrideTemplate(IngredientEntryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IngredientEntryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IngredientEntryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new IngredientEntry(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.ingredientEntry).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
