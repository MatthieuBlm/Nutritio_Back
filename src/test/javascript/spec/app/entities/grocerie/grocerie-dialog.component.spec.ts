/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { NutritioBackTestModule } from '../../../test.module';
import { GrocerieDialogComponent } from '../../../../../../main/webapp/app/entities/grocerie/grocerie-dialog.component';
import { GrocerieService } from '../../../../../../main/webapp/app/entities/grocerie/grocerie.service';
import { Grocerie } from '../../../../../../main/webapp/app/entities/grocerie/grocerie.model';
import { IngredientEntryService } from '../../../../../../main/webapp/app/entities/ingredient-entry';

describe('Component Tests', () => {

    describe('Grocerie Management Dialog Component', () => {
        let comp: GrocerieDialogComponent;
        let fixture: ComponentFixture<GrocerieDialogComponent>;
        let service: GrocerieService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [GrocerieDialogComponent],
                providers: [
                    IngredientEntryService,
                    GrocerieService
                ]
            })
            .overrideTemplate(GrocerieDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GrocerieDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrocerieService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Grocerie(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.grocerie = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'grocerieListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Grocerie();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.grocerie = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'grocerieListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
