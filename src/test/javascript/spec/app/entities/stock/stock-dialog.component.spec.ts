/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { NutritioBackTestModule } from '../../../test.module';
import { StockDialogComponent } from '../../../../../../main/webapp/app/entities/stock/stock-dialog.component';
import { StockService } from '../../../../../../main/webapp/app/entities/stock/stock.service';
import { Stock } from '../../../../../../main/webapp/app/entities/stock/stock.model';
import { IngredientEntryService } from '../../../../../../main/webapp/app/entities/ingredient-entry';

describe('Component Tests', () => {

    describe('Stock Management Dialog Component', () => {
        let comp: StockDialogComponent;
        let fixture: ComponentFixture<StockDialogComponent>;
        let service: StockService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [StockDialogComponent],
                providers: [
                    IngredientEntryService,
                    StockService
                ]
            })
            .overrideTemplate(StockDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StockDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Stock(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.stock = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'stockListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Stock();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.stock = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'stockListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
