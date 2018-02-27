/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { NutritioBackTestModule } from '../../../test.module';
import { IngredientEntryDialogComponent } from '../../../../../../main/webapp/app/entities/ingredient-entry/ingredient-entry-dialog.component';
import { IngredientEntryService } from '../../../../../../main/webapp/app/entities/ingredient-entry/ingredient-entry.service';
import { IngredientEntry } from '../../../../../../main/webapp/app/entities/ingredient-entry/ingredient-entry.model';
import { IngredientService } from '../../../../../../main/webapp/app/entities/ingredient';
import { StockService } from '../../../../../../main/webapp/app/entities/stock';
import { RecipeService } from '../../../../../../main/webapp/app/entities/recipe';
import { GrocerieService } from '../../../../../../main/webapp/app/entities/grocerie';
import { BlackListService } from '../../../../../../main/webapp/app/entities/black-list';

describe('Component Tests', () => {

    describe('IngredientEntry Management Dialog Component', () => {
        let comp: IngredientEntryDialogComponent;
        let fixture: ComponentFixture<IngredientEntryDialogComponent>;
        let service: IngredientEntryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [IngredientEntryDialogComponent],
                providers: [
                    IngredientService,
                    StockService,
                    RecipeService,
                    GrocerieService,
                    BlackListService,
                    IngredientEntryService
                ]
            })
            .overrideTemplate(IngredientEntryDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IngredientEntryDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IngredientEntryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new IngredientEntry(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.ingredientEntry = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'ingredientEntryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new IngredientEntry();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.ingredientEntry = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'ingredientEntryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
